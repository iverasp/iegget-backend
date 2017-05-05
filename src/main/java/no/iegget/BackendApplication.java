package no.iegget;

import no.iegget.blog.model.BlogCategory;
import no.iegget.blog.model.BlogEntry;
import no.iegget.blog.repository.BlogCategoryRepository;
import no.iegget.blog.repository.BlogRepository;
import no.iegget.model.Account;
import no.iegget.eat.model.Recipe;
import no.iegget.repository.AccountRepository;
import no.iegget.eat.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner initRecipes(AccountRepository accountRepository, RecipeRepository recipeRepository) {
        Set<String> sources = new HashSet<>();
        sources.add("matprat.no");
        sources.add("aperitif.no");
		return (evt) -> Arrays.asList(
				"iverasp,sigridhu".split(","))
				.forEach(
						a -> {
							Account account = accountRepository.save(new Account(a, "password", "Iver", "Egge"));
							recipeRepository.save(new Recipe(account, "Brøsmuler", "Brød", "Riv brødet til smuler", sources));
							recipeRepository.save(new Recipe(account, "Kokt vann", "Vann", "Kok vannet", sources));
						}
				);
	}

    @Bean
    CommandLineRunner initBlog(AccountRepository accountRepository, BlogRepository blogRepository, BlogCategoryRepository blogCategoryRepository) {
        BlogCategory tech = blogCategoryRepository.save(new BlogCategory("Technology", "img/cat_tech.jpg"));
        BlogCategory linux = blogCategoryRepository.save(new BlogCategory("Linux", "img/cat_test1.jpg"));
        BlogCategory wood = blogCategoryRepository.save(new BlogCategory("Woodworking", "img/cat_test2.jpg"));
        BlogCategory diy = blogCategoryRepository.save(new BlogCategory("DIY", "img/cat_test3.jpg"));
        BlogCategory travel = blogCategoryRepository.save(new BlogCategory("Travel", "img/cat_test4.jpg"));
        BlogCategory fashion = blogCategoryRepository.save(new BlogCategory("Fashion", "img/cat_test5.jpg"));
        return (evt) -> Arrays.asList(
                "test1".split(","))
                .forEach(
                        a -> {
                            Account account = accountRepository.save(new Account(a, "password", "Iver", "Egge"));
                            blogRepository.save(new BlogEntry(account, "USB HID on the STM32F103C8T6", "Use LibOpenCM3 to connect your project with USB using a $2 microcontroller, without dealing with OS drivers", markdownContent, tech));
                            blogRepository.save(new BlogEntry(account, "Using a PCM5142 DAC with the Raspberry Pi", "A minimalistic DAC with great audio quality and integrated DSP, providing endless possibilities", markdownArticle, linux));
                            blogRepository.save(new BlogEntry(account, "Veneering speaker cabinets", "Different methods of veenering and the results", "testesttest", wood));
                            blogRepository.save(new BlogEntry(account, "Burping bike disc brakes", "Better brake respons in only 5 minutes", "testesttest", diy));
                            blogRepository.save(new BlogEntry(account, "Travel tips", "Good for when you're traveling", "testesttest", travel));
                            blogRepository.save(new BlogEntry(account, "What color?", "You won't believe #6!", "testesttest", fashion));
                        }
                );
    }

    String markdownContent = "```javascript\n" +
            "var s = 2;\n" +
            "alert(s);\n" +
            "```";

    String markdownArticle = "## Prototype board\n" +
            "\n" +
            "A prototype PCB can be found here https://github.com/iverasp/boomdsp\n" +
            "\n" +
            "The PCB is made for I2C operation on address 0x4c (ADDR0 = ADDR1 = 0). All GPIOs are exposed as headers, as well as I2S and the XSMT pin.\n" +
            "\n" +
            "**_XSMT should be tied to 3v3 to unmute the device._**\n" +
            "\n" +
            "Position the 10uF electrolytic capacitor to use VCOM mode. Without the capacitor there should be a jumper connecting VCOM to Gnd and the device will only operate in Vref mode.\n" +
            "\n" +
            "## Interfacing with Raspberry Pi\n" +
            "\n" +
            "Connect the board to the Pis I2C headers. No pullups should be needed as the Pi has internal 1k8 pullups to 3V3. Install i2c-tools and scan for I2C devices\n" +
            "```bash\n" +
            "apt-get install i2c-tools\n" +
            "i2cdetect -y 1\n" +
            "```\n" +
            "\n" +
            "Which should show a device on address 0x4c, such as\n" +
            "\n" +
            "```bash\n" +
            "     0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f\n" +
            "00:          -- -- -- -- -- -- -- -- -- -- -- -- --\n" +
            "10: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --\n" +
            "20: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --\n" +
            "30: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --\n" +
            "40: -- -- -- -- -- -- -- -- -- -- -- -- 4c -- -- --\n" +
            "50: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --\n" +
            "60: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --\n" +
            "70: -- -- -- -- -- -- -- --\n" +
            "```\n" +
            "\n" +
            "### Testing basic I2C commands\n" +
            "\n" +
            "Install python3-smbus\n" +
            "\n" +
            "```bash\n" +
            "apt-get install python3-smbus\n" +
            "```\n" +
            "\n" +
            "Set GPIO6 on the PCM5142 to output and toggle its state with the following code\n" +
            "\n" +
            "```python\n" +
            "import smbus\n" +
            "    \n" +
            "bus = smbus.SMBus(1)\n" +
            "address = 0x4c\n" +
            "    \n" +
            "# select page 0\n" +
            "bus.write_byte_data(address, 0, 0)\n" +
            "    \n" +
            "# set GPIO6 as output\n" +
            "bus.write_byte_data(address, 8, 0x20)\n" +
            "\n" +
            "# set GPIO6 to register output\n" +
            "bus.write_byte_data(address, 85, 0x02)\n" +
            "\n" +
            "# set GPIO6 output high\n" +
            "bus.write_byte_data(address, 86, 0x20)\n" +
            "\n" +
            "# set GPIO6 output low\n" +
            "bus.write_byte_data(address, 86, 0x0)\n" +
            "```\n" +
            "\n" +
            "### Set overlays and load kernel driver\n" +
            "\n" +
            "The Raspberry Pi detects external peripherals if the correct overlay is loaded at boot. It just so happens to be that the IQaudIO DAC+ is based on the PCM5122, and the overlay provided by them works just fine with our little DAC as well (the registers for the PLL and volume control are mostly the same on the PCM512x and PCM514x).\n" +
            "\n" +
            "We therefore only have to make the following adjustments to make the DAC a Linux audio device:\n" +
            "\n" +
            "In __/boot/config.txt__ uncomment the following line\n" +
            "\n" +
            "```bash\n" +
            "dtparams=i2s=on\n" +
            "```\n" +
            "\n" +
            "Comment out the following line\n" +
            "\n" +
            "```bash\n" +
            "#dtparam=audio=on\n" +
            "```\n" +
            "\n" +
            "And add the following line\n" +
            "\n" +
            "```bash\n" +
            "dtoverlay=iqaudio-dacplus\n" +
            "```\n" +
            "\n" +
            "Edit __/etc/asound.conf__ and enter\n" +
            "\n" +
            "```bash\n" +
            "pcm.!default  {\n" +
            "  type hw card 0\n" +
            "}\n" +
            "ctl.!default {\n" +
            "  type hw card 0\n" +
            "}\n" +
            "\n" +
            "```\n" +
            "\n" +
            "After a reboot of the Pi, the following devices should show up when running __aplay -l__\n" +
            "\n" +
            "```bash\n" +
            "card 0: IQaudIODAC [IQaudIODAC], device 0: IQaudIO DAC HiFi pcm512x-hifi-0 []\n" +
            "Subdevices: 0/1\n" +
            "Subdevice #0: subdevice #0\n" +
            "```\n" +
            "\n" +
            "To verify that the Pi now outputs audio via I2S, we can issue\n" +
            "\n" +
            "```bash\n" +
            "speaker-test -c2\n" +
            "```\n" +
            "\n" +
            "which should output a signal on SDOUT (header pin 40 on the Pi).\n" +
            "\n" +
            "Connecting the DAC to the I2S on the Pi and running the same test should result in analog audio being outputted by the DAC.";
}
